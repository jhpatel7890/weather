pipeline{
	agent any
	environment {
		DOCKERHUB_CREDENTIALS=credentials('dockerhub-creds')
	}
	stages {
		stage('Build') {
			steps {
				sh 'gradlew clean build'
			}
		}
		stage('Component Test') {
			steps {
				sh 'gradlew componentTest'
			}
		}
		stage('Generate Docker Image') {
			steps {
                        sh 'docker build -t jhpatel7890/myrepo:weatherprediction-latest .'
                        sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
				sh 'docker push jhpatel7890/myrepo:weatherprediction-latest'
			}
		}
	}
	post {
		always {
			sh 'docker logout'
		}
	}
}
