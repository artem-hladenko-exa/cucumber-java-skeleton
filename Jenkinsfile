pipeline {
    agent any
    triggers {
        // webhook plugin trigger
        GenericTrigger(
                genericRequestVariables: [
                ],
                genericVariables: [
                        [defaultValue: '', key: 'comment', regexpFilter: '', value: '$.comment'],
                        [defaultValue: '', key: 'author', regexpFilter: '', value: '$.author'],
                        [defaultValue: 'localhost:8484', key: 'tr_domain', regexpFilter: '', value: '$.tr_domain'],
                        [defaultValue: '', key: 'tr_suite_id', regexpFilter: '', value: '$.tr_suite_id'],
                        [defaultValue: '', key: 'cucumber_tags', regexpFilter: '', value: '$.cucumber_tags']
                ],
                causeString: 'Triggered by $creator',
                printContributedVariables: true,
                printPostContent: true,
                regexpFilterText: '$branch',
                regexpFilterExpression: "${BRANCH_NAME}",
                silentResponse: false,
                token: 'test-auto'
        )
        // https://JENKINS_URL/generic-webhook-trigger/invoke?token=TOKEN&app_domain=at2&branch=FULL_BRANCH_NAME
    }

    environment {
        RUN_ID = "${tr_suite_id || ""}"
        cucumber_tags = "not @Now"
    }
    tools {
        allure 'allure-cli'
    }
    stages {
        stage('Build') {
            steps {
                sh './gradlew clean build'
            }
        }
        stage('Test') {
            steps {
                echo "./gradlew -Drun.id=${env.RUN_ID} -Dcucumber.options=\"--tags (${cucumber_tags})\" test"
            }
        }
    }
    post {
        always {
            allure results: [[path: '**/build/allure-results']]
        }
    }
}
