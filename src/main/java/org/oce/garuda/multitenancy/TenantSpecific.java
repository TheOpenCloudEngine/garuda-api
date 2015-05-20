

package org.oce.garuda.multitenancy;

import java.rmi.RemoteException;

import javax.jws.WebService;


@WebService
public interface TenantSpecific {
	
	public String getMetadata(String tenantId, String appKey, String metaDataKey);
	public String initProcess(String tenantId, String appKey, String metaDataKey) throws RemoteException;
	public void executeProcess(String instanceId);
	public void setProcessVariable(String instanceId, String variableKey, String value);
	public String getProcessVariable(String instanceId, String variableKey);
	
	public AppDbRepositorySimple getAppDbRepository(String tenantId, String appKey) throws Exception;

}
