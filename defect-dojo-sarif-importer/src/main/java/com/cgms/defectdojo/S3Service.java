package com.cgms.defectdojo;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.services.s3.paginators.ListObjectsV2Iterable;

import java.io.File;
import java.io.IOException;

@Service
@Slf4j
public class S3Service
{
    @Autowired
    private S3Client s3Client;

    @Value( "${s3.bucket-name}" )
    private String bucketName;

    public void downloadLatestSarifFiles() throws IOException
    {

        log.debug( "Listing objects in bucket: {}", this.bucketName );

        ListObjectsV2Iterable listObjectsV2Iterable = this.s3Client.listObjectsV2Paginator( r -> r.bucket( bucketName ) );
        GetObjectRequest getObjectRequest = null;

        log.debug( "Iterating through Listed objects in bucket: {}", this.bucketName );
        for ( S3Object objectSummary : listObjectsV2Iterable.contents() )
        {
            if ( objectSummary.key().endsWith( ".sarif" ) &&
                    FileSystem.getCurrent().isLegalFileName( objectSummary.key() ) &&
                    !FileSystem.getCurrent().isReservedFileName( objectSummary.key() ) )
            {
                log.debug( "Found valid Sarif file: {}", objectSummary.key() );

                getObjectRequest = GetObjectRequest.builder()
                        .bucket( this.bucketName )
                        .key( objectSummary.key() )
                        .build();

                log.debug( "Downloading Sarif file: {}", objectSummary.key() );

                this.s3Client.getObject( getObjectRequest, File.createTempFile( objectSummary.key(), ".sarif" ).toPath() );
            }
        }
    }

    public void moveS3SarifFileToProcessedFolder( String ... fileName )
    {
        log.debug( "Moving Sarif file to processed folder" );

        for( String name : fileName)
        {
            log.debug( "Moving Sarif file {} to processed folder", name );

        }
    }
}
