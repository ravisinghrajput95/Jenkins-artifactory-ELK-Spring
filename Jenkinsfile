pipeline{
    agent any
    
     tools {
       maven 'maven'
   }
      environment {
       DOCKER_TAG = getVersion()
    }
    
   stages{
     stage('Code checkout'){
        steps{
            git branch: 'main', credentialsId: 'github', url: 'https://github.com/ravisinghrajput95/Jenkins-artifactory-ELK-Spring.git'
        }
    }
     
    stage('Build'){
        steps{
          sh 'mvn clean package'
        }
    }
    
    stage ('Artifactory configuration') {
        steps {
          rtServer (
            id: "jfrog",
            url: "http://34.125.113.160:8081/artifactory",
            credentialsId: "jfrog"
             )
          rtMavenDeployer (
            id: "MAVEN_DEPLOYER",
            serverId: "jfrog",
            releaseRepo: "sample-libs-release-local",
            snapshotRepo: "sample-libs-snapshot-local"
             )

          rtMavenResolver (
            id: "MAVEN_RESOLVER",
            serverId: "jfrog",
            releaseRepo: "sample-libs-release",
            snapshotRepo: "sample-libs-snapshot"
            )
        }
    }
    
     stage ('Deploy Artifacts') {
       steps {
         rtMavenRun (
           tool: "maven",
           pom: 'pom.xml',
           goals: 'clean install',
           deployerId: "MAVEN_DEPLOYER",
           resolverId: "MAVEN_RESOLVER"
        )
      }
    }
    
    stage ('Publish build info') {
      steps {
        rtPublishBuildInfo (
          serverId: "jfrog"
        )
      }
    }
    
    stage('docker-build'){
    steps{
        sh 'docker build -t rajputmarch2020/java_app:${DOCKER_TAG} .'
    }
    }
    
    stage('DockerHub-image-push'){
      steps{
        withCredentials([string(credentialsId: 'dockerhub', variable: 'password')]) {
          sh 'docker login -u rajputmarch2020 -p ${password} '
                }
          sh 'docker push rajputmarch2020/java_app:${DOCKER_TAG}'
            }
        }
        
    stage('QA-Deployment'){
      steps{
        ansiblePlaybook credentialsId: 'QA-server', 
        disableHostKeyChecking: true, 
        extras: "-e DOCKER_TAG=${DOCKER_TAG}", 
        installation: 'ansible', 
        inventory: 'environment.inv',
        playbook: 'deploy_QA.yaml'
      }
   }
      
    stage('Approval to Prod deployment'){
      steps{
        timeout(time: 15, unit: 'MINUTES'){
        input message: 'Do you approve deployment for production?' , ok: 'Yes'
      }
    }
  }
     
		stage('Prod-Deployment'){
      steps{
        ansiblePlaybook credentialsId: 'Prod-server', 
        disableHostKeyChecking: true, 
        extras: "-e DOCKER_TAG=${DOCKER_TAG}", 
        installation: 'ansible', 
        inventory: 'environment.inv',
        playbook: 'deploy_Prod.yaml'
                
     }
   }

}
}
 def getVersion(){
   def commitHash =  sh returnStdout: true, script: 'git rev-parse --short HEAD'
   return commitHash
}
