pipeline{
	agent any
		stages{
			stage('Docker Build'){
			        steps{
				     	      docker images -a
				              cd azure-vote/
				              docker images -a 
				              docker build -t jenkins-pipeline .
				              docker images -a
				              cd ..
			}
		}
}
}
