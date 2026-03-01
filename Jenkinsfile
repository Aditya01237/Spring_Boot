pipeline {
    agent any

    stages {

        stage('Clone Repository') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/Aditya01237/Spring_Boot.git'
            }
        }

        stage('Build Backend (Maven)') {
            steps {
                dir('portal') {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                dir('portal') {
                    sh 'docker build -t placement-backend .'
                }
            }
        }
        
        stage('Build Frontend Docker Image') {
            steps {
                dir('placement-frontend') {
                    sh 'docker build -t placement-frontend .'
                }
            }
        }

        stage('Deploy Using Ansible') {
            steps {
                dir('devops-ansible') {
                    sh 'ansible-playbook -i inventory.ini deploy.yml'
                }
            }
        }
    }
}
