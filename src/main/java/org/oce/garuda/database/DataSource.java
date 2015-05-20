package org.oce.garuda.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.commons.dbcp.BasicDataSource;
import org.oce.garuda.multitenancy.AppDbRepositorySimple;
import org.oce.garuda.multitenancy.TenantSpecific;
import org.uengine.cloud.saasfier.MultiTenantedDataSource;

public class DataSource extends MultiTenantedDataSource{
	
	HashMap<String, AppDbRepositorySimple> appDbRepos = new HashMap<String, AppDbRepositorySimple>(); 

	@Override
	public Connection getConnection() throws SQLException {
		
		if(getUrl() == null){
			getAppKey();
		}
		
		return super.getConnection();
	}
	
	TenantSpecific tenantSpecific;
	
		public TenantSpecific getTenantSpecific() {
			return tenantSpecific;
		}
	
		public void setTenantSpecific(TenantSpecific tenantSpecific) {
			this.tenantSpecific = tenantSpecific;
		}

	@Override
	protected BasicDataSource createTenantDataSource(String tenantId, String url, String uid, String pwd) {
		AppDbRepositorySimple dbRepoForTenant;
		
		if(!appDbRepos.containsKey(tenantId)){
			
			try {
				dbRepoForTenant = tenantSpecific.getAppDbRepository(tenantId, getAppKey());
				appDbRepos.put(tenantId, dbRepoForTenant);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			
		}else{
			dbRepoForTenant = appDbRepos.get(tenantId);
		}
		

		return super.createTenantDataSource(tenantId, dbRepoForTenant.getConnectionUrl(), dbRepoForTenant.getUserId(), dbRepoForTenant.getPassword() );
					
	}
	
	String appKey;
	
		public String getAppKey() {
			return appKey;
		}
	
		public void setAppKey(String appKey) {
			this.appKey = appKey;
		}
		
	String portalServer;
	
		public String getPortalServer() {
			return portalServer;
		}
	
		public void setPortalServer(String portalServer) {
			this.portalServer = portalServer;
		}


}
