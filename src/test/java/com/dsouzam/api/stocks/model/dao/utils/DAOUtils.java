package com.dsouzam.api.stocks.model.dao.utils;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

public enum DAOUtils {

	INSTANCE;

	public AmazonDynamoDBClient dynamoDB = null;

	public AmazonDynamoDBClient getDynamoDB() {
		if (dynamoDB == null) {

			AWSCredentials credentials = null;
			try {
				credentials = new ProfileCredentialsProvider("default")
						.getCredentials();
			} catch (Exception e) {
				throw new AmazonClientException(
						"Cannot load the credentials from the credential profiles file. "
								+ "Please make sure that your credentials file is at the correct "
								+ "location (/home/mndsouza/.aws/credentials), and is in valid format.",
						e);
			}
			dynamoDB = new AmazonDynamoDBClient(credentials);
			Region usWest2 = Region.getRegion(Regions.US_WEST_2);
			dynamoDB.setRegion(usWest2);
		}
		return dynamoDB;
	}
}
