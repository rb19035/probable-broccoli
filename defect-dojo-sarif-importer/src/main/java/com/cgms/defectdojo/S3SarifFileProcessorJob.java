package com.cgms.defectdojo;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class S3SarifFileProcessorJob implements Job
{

    @Override
    public void execute( JobExecutionContext jobExecutionContext ) throws JobExecutionException
    {
        log.debug( "Executing S3 SARIF file processor job" );


    }
}
