package nanohttpd.protocols.http.tempfiles;

/*
 * #%L
 * NanoHttpd-Core
 * %%
 * Copyright (C) 2012 - 2016 nanohttpd
 * %%

 * #L%
 */

/**
 * Temp file manager.
 * <p/>
 * <p>
 * Temp file managers are created 1-to-1 with incoming requests, to create and
 * cleanup temporary files created as a result of handling the request.
 * </p>
 */
public interface ITempFileManager {

    void clear();
    public ITempFile createTempFile(String filename_hint) throws Exception;
}
