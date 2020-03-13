package com.smatechnologies.msazure.storage.connector;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.prefs.Preferences;

import org.ini4j.Ini;
import org.ini4j.IniPreferences;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.smatechnologies.msazure.storage.arguments.ConnectorArguments;
import com.smatechnologies.msazure.storage.config.ConnectorConfig;
import com.smatechnologies.msazure.storage.connector.impl.StorageConnectorImpl;
import com.smatechnologies.msazure.storage.interfaces.ConnectorConstants;
import com.smatechnologies.msazure.storage.modules.StorageInformation;
import com.smatechnologies.msazure.storage.routines.Util;

public class ListContainersTest {

	private static final String connect = "DefaultEndpointsProtocol=https;AccountName=bvhtest;AccountKey=7dQZrpv/gZN6sn6P8kPbzNj1DefOSxWVcJmBsoCLXvdjCfOWfMDwtHlMyzOzropwkzSHucAk5Ca5kpwS/blcOw==;EndpointSuffix=core.windows.net";

	private static ConnectorConfig _ConnectorConfig = ConnectorConfig.getInstance();
	private static Util _Util = new Util();

	
	public static void main( String[] args ) throws IOException {
		StorageConnectorImpl storageConnectorImpl = new StorageConnectorImpl();
		String workingDirectory = null;
		String configFileName = null;
		
		try {
			workingDirectory = System.getProperty(ConnectorConstants.SYSTEM_USER_DIRECTORY);
			// go get information from config file
			configFileName = workingDirectory + File.separator + ConnectorConstants.CONFIG_FILE_NAME;
			Preferences iniPrefs = new IniPreferences(new Ini(new File(configFileName)));
	       	_ConnectorConfig = _Util.setConfigurationValues(iniPrefs, _ConnectorConfig, configFileName);
			ConnectorArguments arguments = new ConnectorArguments();
			arguments.setStorageAccount("bvhtest");
			arguments.setFunction("information");
			arguments.setOperation("containers");
			arguments.setContainerName("ALL");
        	StorageInformation info = _ConnectorConfig.getStorageInformation(arguments.getStorageAccount().toLowerCase());
        	info.setConnection(connect);
			boolean success = storageConnectorImpl.processInformationRequest(arguments, info);
			
		} catch (Exception ex) {
			System.out.println("ex : " + ex.getMessage());
			ex.printStackTrace();
		}
	}

}
