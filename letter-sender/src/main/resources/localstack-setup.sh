#!/bin/sh

AWS_REGION="us-east-1"

echo "Initializing localstack s3"

export AWS_DEFAULT_REGION=$AWS_REGION

awslocal sns create-topic --name ChristmasLetterSns --region $AWS_REGION|| true

awslocal sqs create-queue --queue-name ChristmasLetterQueue --region $AWS_REGION|| true


echo "Starting LocalStack using Docker Compose."
docker-compose up -d

echo Waiting for LocalStack to initialize...
timeout /t 10

echo "Creating DynamoDB table: SantaChristmasLetters"
awslocal dynamodb create-table \
--table-name SantaChristmasLetters \
--attribute-definitions AttributeName=Email,AttributeType=S \
--key-schema AttributeName=Email,KeyType=HASH \
--provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5 \
--region $AWS_REGION

echo "Waiting for LocalStack to initialize..."
until awslocal sns list-topics &> /dev/null; do
echo "Waiting for LocalStack..."
sleep 5
done

echo "Adding example item to the DynamoDB table..."
awslocal dynamodb put-item \
--table-name SantaChristmasLetters \
--item '{
"Email": {"S": "cristina.stoenescu93@gmail.com"},
"name": {"S": "Cristina"},
"wishes": {"S": "Dear Santa, I wish a professional Lego."},
"location": {"M": {"latitude": {"N": "48.8566"}, "longitude": {"N": "2.3522"}}}
}'\
--region $AWS_REGION

echo "Subscribing SNS topic: ChristmasLetterSns"
awslocal sns subscribe --topic-arn arn:aws:sns:us-east-1:000000000000:ChristmasLetterSns --protocol sqs --notification-endpoint arn:aws:sqs:us-east-1:000000000000:ChristmasLetterQueue || true


echo "Resource initialization completed!!"