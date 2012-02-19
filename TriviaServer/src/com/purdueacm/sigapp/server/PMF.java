package com.purdueacm.sigapp.server;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

/**
 * Singleton wrapper for PersistenceManagerFactory.
 * @author Eric Templin
 *
 */
public final class PMF {
    private static final PersistenceManagerFactory pmfInstance =
        JDOHelper.getPersistenceManagerFactory("transactions-optional");

    private PMF() {}

    public static PersistenceManagerFactory get() {
        return pmfInstance;
    }
}