def runTest(def id, def tags) {
    sh("./gradlew test -DRUN_ID=\\\"${id}\\\" -Dcucumber.options=\\\"--tags \\\'\\(${tags}\\)\\\'\\\"")
}

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
                silentResponse: false,
                token: 'test-auto'
        )
        // https://JENKINS_URL/generic-webhook-trigger/invoke?token=TOKEN&app_domain=at2&branch=FULL_BRANCH_NAME
    }

    environment {
        CUCUMBER_TAGS = "${cucumber_tags}"
        SUIT_ID = "${tr_suite_id}"
    }
    tools {
        allure 'allure-cli'
    }
    stages {
        stage('Test') {
            steps {
                echo "${cucumber_tags}"
                script {
                    runTest(env.SUIT_ID, env.CUCUMBER_TAGS)
                }

            }
        }
    }
    post {
        always {
            allure results: [[path: '**/build/allure-results']]
        }
    }
}
