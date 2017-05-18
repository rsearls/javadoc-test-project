package org.jboss.wise.test.integration.complex;

import javax.xml.ws.WebFault;

/**
 * This class was generated by Apache CXF 2.7.3-SNAPSHOT (1439267) 2013-02-20T16:44:23.116+01:00 Generated source version:
 * 2.7.3-SNAPSHOT
 */

@WebFault(name = "AlreadyRegisteredFault", targetNamespace = "http://types.complex.jaxws.ws.test.jboss.org/")
public class AlreadyRegisteredFault_Exception extends Exception {

    private org.jboss.wise.test.integration.complex.AlreadyRegisteredFault alreadyRegisteredFault;

    public AlreadyRegisteredFault_Exception() {
        super();
    }

    public AlreadyRegisteredFault_Exception(String message) {
        super(message);
    }

    public AlreadyRegisteredFault_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyRegisteredFault_Exception(String message,
            org.jboss.wise.test.integration.complex.AlreadyRegisteredFault alreadyRegisteredFault) {
        super(message);
        this.alreadyRegisteredFault = alreadyRegisteredFault;
    }

    public AlreadyRegisteredFault_Exception(String message,
            org.jboss.wise.test.integration.complex.AlreadyRegisteredFault alreadyRegisteredFault, Throwable cause) {
        super(message, cause);
        this.alreadyRegisteredFault = alreadyRegisteredFault;
    }

    public org.jboss.wise.test.integration.complex.AlreadyRegisteredFault getFaultInfo() {
        return this.alreadyRegisteredFault;
    }
}
