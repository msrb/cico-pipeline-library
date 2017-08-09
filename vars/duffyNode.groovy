import jenkins.model.*
import hudson.model.*
import hudson.slaves.*
import hudson.plugins.sshslaves.*
import java.util.ArrayList;
import java.util.UUID;


def call(Closure body) {

    final String nodeName = "duffy-" + UUID.randomUUID().toString();

    try {

        addNode(nodeName)
        node('master') {
            body()
        }

    } finally {
        //removeNode(nodeName)
    }
}

@NonCPS
def addNode(nodeName) {
    Node node = new DumbSlave(
                    nodeName,
                    "Duffy node for ${env.JOB_NAME} ${env.BUILD_NUMBER}",
                    "/home/root/",
                    "1",
                    Node.Mode.EXCLUSIVE,
                    nodeName,
                    new SSHLauncher("agenNode", 22, "user", "password", "", "", "", "", ""),
                    new RetentionStrategy.Always(),
                    new LinkedList())
    Jenkins.instance.addNode(node)
}

@NonCPS
def removeNode(nodeName) {
    Node node = Jenkins.instance.getNode(nodeName)
    if (node != null) {
        Jenkins.instance.removeNode(node)
    }
}
