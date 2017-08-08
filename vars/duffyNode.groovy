import jenkins.model.*
import hudson.model.*
import hudson.slaves.*
import hudson.plugins.sshslaves.*
import java.util.ArrayList;

import java.util.UUID;

def call(Closure body) {

    String nodeName = "duffy-" + UUID.randomUUID().toString();

    //final Slave slave = new DumbSlave(
    //                        nodeName,
    //                        "Agent node description",
    //                        "/home/jenkins",
    //                        "1",
    //                        Node.Mode.EXCLUSIVE,
    //                        nodeName,
    //                        new SSHLauncher("agenNode", 22, "user", "password", "", "", "", "", ""),
    //                        new RetentionStrategy.Always(),
    //                        new LinkedList())
    try {

        addNode(nodeName)

        //node {
        //    body()
        //}

    } finally {
        removeNode(nodeName)
    }
}

@NonCPS
def addNode(nodeName) {
    Jenkins.instance.addNode(getSlave(nodeName))
}

@NonCPS
def removeNode(nodeName) {
    Jenkins.instance.removeNode(Jenkins.instance.getNode(nodeName))
}

@NonCPS
def getSlave(nodeName) {
    return new DumbSlave(
               nodeName,
               "Agent node description",
               "/home/jenkins",
               "1",
               Node.Mode.EXCLUSIVE,
               nodeName,
               new SSHLauncher("agenNode", 22, "user", "password", "", "", "", "", ""),
               new RetentionStrategy.Always(),
               new LinkedList())
}
