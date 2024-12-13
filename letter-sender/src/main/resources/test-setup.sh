AWS_REGION="us-east-1"

export AWS_DEFAULT_REGION=$AWS_REGION

awslocal sns create-topic --name ChristmasLetterSns --region $AWS_REGION|| true
awslocal sqs create-queue --queue-name ChristmasLetterQueue --region $AWS_REGION|| true

awslocal sns subscribe --topic-arn arn:aws:sns:us-east-1:000000000000:ChristmasLetterSns --protocol sqs --notification-endpoint arn:aws:sqs:us-east-1:000000000000:ChristmasLetterQueue || true