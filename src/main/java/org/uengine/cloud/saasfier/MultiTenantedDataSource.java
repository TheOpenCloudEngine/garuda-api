package org.uengine.cloud.saasfier;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.commons.dbcp.AbandonedConfig;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;

import org.oce.garuda.multitenancy.TenantContext;

public class MultiTenantedDataSource implements DataSource{

	public MultiTenantedDataSource(){
		defaultAutoCommit = true;
		defaultReadOnly = null;
		defaultTransactionIsolation = -1;
		defaultCatalog = null;
		driverClassName = null;
		maxActive = 8;
		maxIdle = 8;
		minIdle = 0;
		initialSize = 0;
		maxWait = -1L;
		poolPreparedStatements = false;
		maxOpenPreparedStatements = -1;
		testOnBorrow = true;
		testOnReturn = false;
		timeBetweenEvictionRunsMillis = -1L;
		numTestsPerEvictionRun = 3;
		minEvictableIdleTimeMillis = 1800000L;
		testWhileIdle = false;
		password = null;
		url = null;
		username = null;
		validationQuery = null;
		accessToUnderlyingConnectionAllowed = false;
		restartNeeded = false;
		connectionPool = null;
		connectionProperties = new Properties();
		dataSource = null;
		logWriter = new PrintWriter(System.out);
	}

	public synchronized String getPassword()
	{
		return password;
	}

	public synchronized void setPassword(String password)
	{
		this.password = password;
		restartNeeded = true;
	}

	public synchronized String getUrl()
	{
		return url;
	}

	public synchronized void setUrl(String url)
	{
		this.url = url;
		restartNeeded = true;
	}

	public synchronized String getUsername()
	{
		return username;
	}

	public synchronized void setUsername(String username)
	{
		this.username = username;
		restartNeeded = true;
	}

	public synchronized String getValidationQuery()
	{
		return validationQuery;
	}

	public synchronized void setValidationQuery(String validationQuery)
	{
		if(validationQuery != null && validationQuery.trim().length() > 0)
			this.validationQuery = validationQuery;
		else
			this.validationQuery = null;
		restartNeeded = true;
	}

	public synchronized String getDriverClassName()
	{
		return driverClassName;
	}

	public synchronized void setDriverClassName(String driverClassName)
	{
		if(driverClassName != null && driverClassName.trim().length() > 0)
			this.driverClassName = driverClassName;
		else
			this.driverClassName = null;
		restartNeeded = true;
	}

	HashMap<String, BasicDataSource> baseDataSources = new HashMap<String, BasicDataSource>();

	public Connection getConnection() throws SQLException {

		String tenantId = TenantContext.getThreadLocalInstance().getTenantId();
		BasicDataSource dataSourceForTenant = null;

		if(!baseDataSources.containsKey(tenantId)){

			dataSourceForTenant = createTenantDataSource(tenantId, getUrl(), getUsername(), getPassword());
		}else{
			System.out.println("dataSourceForTenant get : " + tenantId);
			
			dataSourceForTenant = baseDataSources.get(tenantId);
		}

		return dataSourceForTenant.getConnection();
	}

	protected BasicDataSource createTenantDataSource(String tenantId, String url, String uid, String pwd) {
		BasicDataSource dataSourceForTenant;
		dataSourceForTenant = new BasicDataSource();
		dataSourceForTenant.setUrl(url);
		dataSourceForTenant.setUsername(uid);
		dataSourceForTenant.setPassword(pwd);
		dataSourceForTenant.setValidationQuery(getValidationQuery());
		dataSourceForTenant.setDriverClassName(getDriverClassName());

		System.out.println("dataSourceForTenant create : " + tenantId);
		
		baseDataSources.put(tenantId, dataSourceForTenant);
		return dataSourceForTenant;
	}

	public synchronized void close() throws SQLException {
		Iterator<String> iterator = baseDataSources.keySet().iterator();
	    while (iterator.hasNext()) {
	        String key = (String) iterator.next();
	        
	        System.out.println("dataSourceForTenant close : " + key);
	        
	        BasicDataSource dataSourceForTenant = baseDataSources.get(key);
	        dataSourceForTenant.close();
	    }
	}

	@Override
	public Connection getConnection(String username, String password)
			throws SQLException {
		throw new UnsupportedOperationException("Not supported by MultiTenantedDataSource");
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		// TODO Auto-generated method stub

	}
	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		// TODO Auto-generated method stub

	}
	@Override
	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	protected boolean defaultAutoCommit;
	protected Boolean defaultReadOnly;
	protected int defaultTransactionIsolation;
	protected String defaultCatalog;
	protected String driverClassName;
	protected int maxActive;
	protected int maxIdle;
	protected int minIdle;
	protected int initialSize;
	protected long maxWait;
	protected boolean poolPreparedStatements;
	protected int maxOpenPreparedStatements;
	protected boolean testOnBorrow;
	protected boolean testOnReturn;
	protected long timeBetweenEvictionRunsMillis;
	protected int numTestsPerEvictionRun;
	protected long minEvictableIdleTimeMillis;
	protected boolean testWhileIdle;
	protected String password;
	protected String url;
	protected String username;
	protected String validationQuery;
	private boolean accessToUnderlyingConnectionAllowed;
	private boolean restartNeeded;
	protected GenericObjectPool connectionPool;
	protected Properties connectionProperties;
	protected DataSource dataSource;
	protected PrintWriter logWriter;
	private AbandonedConfig abandonedConfig;

	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

}