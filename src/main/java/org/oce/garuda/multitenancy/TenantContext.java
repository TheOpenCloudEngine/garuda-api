package org.oce.garuda.multitenancy;



public class TenantContext{
	
    static ThreadLocal<TenantContext> local = new ThreadLocal<TenantContext>();

    String tenantId;
		public String getTenantId() {
			return tenantId;
		}
		public void setTenantId(String tenantId) {
			this.tenantId = tenantId;
		}


	public TenantContext(String tenantId){
//		if(getThreadLocalInstance()!=null)
//			throw new RuntimeException("There's uncommitted transactionContext remains.");
		
		this.tenantId = tenantId;
		
//new Throwable("TenantId is recognized: " + tenantId).printStackTrace();
		
		local.set(this);

	}

	public static Object nonTenantSpecificOperation(Operation logic){
		String tenantId = getThreadLocalInstance().getTenantId();

		new TenantContext(null);

		Object returnValue = logic.run();

		new TenantContext(tenantId);

		return returnValue;
	}
	

	
	public static TenantContext getThreadLocalInstance(){
		TenantContext tc = local.get();
		
		if(tc != null)
			return tc;
		else
			return new TenantContext(null);
	}

	
}