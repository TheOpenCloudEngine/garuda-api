package org.oce.garuda.multitenancy;

import java.rmi.RemoteException;


public class TenantSpecificUtil {
	
	TenantSpecific tenantSpecific;

		public TenantSpecific getTenantSpecific() {
			return tenantSpecific;
		}
	
		public void setTenantSpecific(TenantSpecific tenantSpecific) {
			this.tenantSpecific = tenantSpecific;
		}

	String metadataServer;

		public String getMetadataServer() {
			return metadataServer;
		}

		public void setMetadataServer(String metadataServer) {
			this.metadataServer = metadataServer;
		}


	String appKey;

		public String getAppKey() {
			return appKey;
		}
	
		public void setAppKey(String appKey) {
			this.appKey = appKey;
		}

	public String getMetadata(String metaDataKey){
		return tenantSpecific.getMetadata(TenantContext.getThreadLocalInstance().getTenantId(), getAppKey(), metaDataKey);
	}
	
	
	public String initProcess(String processDefId) throws RemoteException{
		return tenantSpecific.initProcess(TenantContext.getThreadLocalInstance().getTenantId(), getAppKey(), processDefId);
		
	}

	public String getSelfServicePortalURL(){
		return "http://" + TenantContext.getThreadLocalInstance().getTenantId() + "." + getMetadataServer() + "/selfservice/" + getAppKey();
	}

	public String getIamageURL(String imageMetadatKey){
		return "http://" +TenantContext.getThreadLocalInstance().getTenantId() + "." + getMetadataServer() + "/tenantImage/" + getAppKey() + "/" + imageMetadatKey;
	}



//	public void executeProcess(String instanceId);
//	public void setProcessVariable(String instanceId, String variableKey, String value);
//	public String getProcessVariable(String instanceId, String variableKey);
//	


}
