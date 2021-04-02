package com.yourdataconnect.salesforceAuth;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Properties;

public class Main {

	public static void main(String[] args) throws ConnectionException, IOException, SQLException, ParseException {
		InputStream input=new FileInputStream(args[0]);
		Properties prop=new Properties();
		prop.load(Input);
		ConnectorConfig config= new ConnectorConfig();
		config.setUsername(prop.getProperty("Salesforce.username"));
		config.setPassword(prop.getProperty("Salesforce.password"));
		config.setAuthEndpoint(prop.getProperty("salesforce.endpoint"));
		EnterpriseConnection connection = Connector.newConnection(config);
		String url = null,site=null,username=null,password=null;
		String getbiserver="select URl__c,Username__c,Site__c,Password__c from BI__c where Id=' "+prop.getProperty("salesforce.biserver.recordId")+" ' ";
		QueryResult queryResults=connection.query(getbiserver);
		if(queryResults.getSize()>0) {
			for(int i=0;i<queryResults.getRecords().length;i++) {
				BI__c biserver = (BI__c)queryResults.getRecords()[i];
				url biserver.getURL__c();
				site =biserver.getSite__c();
				username=biserver.getUsername__c();
				password=biserver.getPassword__c();
				
			}
		}
		TableauAPI api=new TableauAPI(username,password,site,url);
		BISite.getBISites(connection, api, url,prop.getProperty("salesforce.biserver.recordId"));
		BIProjects.getBIProjects(connection, api, url);
		BIWorkbooks.getBIWorkbooks(connection, api,url);
		BIDashboards.getBIDashboards(connection,api,url,args[0]);
		BISheets.getBISheets(connection,api,url.args[0]);
		BIDashboardSheets.getBIDashboardSheets(connection,api);
		BIFields.getBIFields(connection, api);
		BISheetFields.getBISheetFields(connection, api);

	}

}
