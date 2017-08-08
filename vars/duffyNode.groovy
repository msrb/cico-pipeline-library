import jenkins.model.*
import hudson.model.*
import hudson.slaves.*
import hudson.plugins.sshslaves.*
import java.util.ArrayList;

import java.util.UUID;

def call(Closure body) {

    String nodeName = "duffy-" + UUID.randomUUID().toString();

    final Slave slave

    try {
        slave = new DumbSlave(
                    nodeName,
                    "Agent node description",
                    "/home/jenkins",
                    "1",
                    Node.Mode.EXCLUSIVE,
                    nodeName,
                    new SSHLauncher("agenNode", 22, "user", "password", "", "", "", "", ""),
                    new RetentionStrategy.Always(),
                    new LinkedList())

        addNode(slave)

        node(nodeName) {
            body()
        }

    } finally {
        if (slave != null) {
            removeNode(slave)
        }
    }
}

@NonCPS
def addNode(slave) {
    Jenkins.instance.addNode(slave)
}

@NonCPS
def removeNode(slave) {
    Jenkins.instance.removeNode(slave)
}

