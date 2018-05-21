package nanohttpd.protocols.http.response;

/*
 * #%L
 * NanoHttpd-Core
 * %%
 * Copyright (C) 2012 - 2016 nanohttpd
 * %%

 * #L%
 */

public interface IStatus {

    String getDescription();

    int getRequestStatus();
}
