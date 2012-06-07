/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.proteomecommons.t2util;

import java.util.HashSet;
import java.util.Set;
import org.proteomecommons.T2DE.T2Interface.DBConnectionData;
import org.proteomecommons.T2DE.T2Interface.DataBuilder;
import org.proteomecommons.T2DE.T2Interface.DataBuilderInterface;
import org.proteomecommons.T2DE.T2Interface.Database;
import org.proteomecommons.T2DE.T2Interface.FolderInterface;
import org.proteomecommons.T2DE.T2Interface.FolderListInterface;
import org.proteomecommons.t2util.utils.AssertionUtil;

/**
 *
 * @author Bryan Smith - bryanesmith@gmail.com
 */
public class T2Instrument {

    public static final String UNIDENTIFIED_INSTRUMENT_IP = "141.211.95.185";
    public static final String AK048_INSTRUMENT_IP = "141.211.95.218";

    public static final String DEFAULT_IP = AK048_INSTRUMENT_IP;
    public static final int DEFAULT_PORT = 1521;

    protected Database database;
    protected DataBuilderInterface dbInterface;
    private final String ip;
    private final int port;
    // Lazy-loaded variables
    private Set<SpotSetDescription> spotSetDescriptions = null;

    /**
     *
     * @param ip
     * @param port
     * @throws Exception
     */
    private T2Instrument(String ip, int port) throws Exception {

        AssertionUtil.assertNotNull("The IP address must be specified to connect to instrument.", ip);

        this.ip = ip;
        this.port = port;

        // Build the database proxy object
        database = new Database(new DBConnectionData(ip, String.valueOf(port)));
        database.initConnection();

        // Hmmm... wrap the proxy in an interface? =)
        dbInterface = DataBuilder.getInstance(database);
    }

    /**
     *
     * @return
     */
    public Set<SpotSetDescription> getSpotDescriptions() {
        if (spotSetDescriptions == null) {
            spotSetDescriptions = new HashSet();

            FolderListInterface foldersInterface = dbInterface.getFolders();
            FolderInterface[] folders = foldersInterface.toArray();

            for (FolderInterface folder : folders) {
                SpotSetDescription spotSet = new SpotSetDescription(folder.getFolderId(), folder.getName(), this);
                spotSetDescriptions.add(spotSet);
            }
        }
        return spotSetDescriptions;
    }

    /**
     * <p>Returns an instrument, which provides accessor methods for spot sets.</p>
     * @param ip
     * @param port
     * @return
     */
    public static T2Instrument connect(String ip, int port) throws Exception {
        return new T2Instrument(ip, port);
    }

    /**
     * <p>Disconnects from instrument and frees up all resources from child nodes.</p>
     */
    public void close() {
        if (spotSetDescriptions != null) {
            for (SpotSetDescription nextSpotSet : this.spotSetDescriptions) {
                nextSpotSet.close();
            }
            spotSetDescriptions = null;
        }

        this.database.disconnect();
    }

    /**
     * @return the ip
     */
    public String getIP() {
        return ip;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }
}
