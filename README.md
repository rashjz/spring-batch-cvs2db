# spring-batch-cvs2db

####Spring Batch application contains 2 step jobs 

#####1 reading from csv file and writing to mysql db 
      <batch:step id="step1">
            <batch:tasklet>
                <batch:chunk reader="cvsFileItemReader" writer="mysqlItemWriter" processor="itemProcessor"
                             commit-interval="1">
                </batch:chunk>
            </batch:tasklet>
        </batch:step>

first check reader="cvsFileItemReader" 
<bean id="cvsFileItemReader" class="org.springframework.batch.item.file.FlatFileItemReader">

        <property name="resource" value="classpath:cvs/report.csv"/>

        <property name="lineMapper">
            <bean class="org.springframework.batch.item.file.mapping.DefaultLineMapper">
                <property name="lineTokenizer">
                    <bean
                            class="org.springframework.batch.item.file.transform.DelimitedLineTokenizer">
                        <property name="names" value="id,name,surname,transaction,date"/>
                    </bean>
                </property>
                <property name="fieldSetMapper">
                    <bean class="rashjz.info.mapper.ReportFieldSetMapper"/>
                </property>
            </bean>
        </property>

    </bean>
here we used lineMapper - org.springframework.batch.item.file.mapping.DefaultLineMapper class that reads lines for delimiter for sequence on names property
then maps all this values to our Report class step goes to writer:

    <bean id="mysqlItemWriter" class="org.springframework.batch.item.database.JdbcBatchItemWriter">
        <property name="dataSource" ref="dataSource"/>
        <property name="sql">
            <value>
                <![CDATA[
			insert into JOB_REPORT(id,name,surname,transaction,date) values (?, ?, ?, ?, ?)
			 ]]>
            </value>
        </property> 
        <property name="ItemPreparedStatementSetter">
            <bean class="rashjz.info.job.ReportItemPreparedStatementSetter"/>
        </property>
    </bean>

and writer initialize datasource and sql query then invokes PreparedStatement to write to db 
#####2 reading from db and wiriting to xml file 
step 2 like a step 1 we must first read data here we start to read data from mysql db and then write to xml file 

       <batch:step id="step2">
            <batch:tasklet>
                <batch:chunk reader="itemReader" writer="itemWriter" processor="itemProcessor" commit-interval="1">
                </batch:chunk>
            </batch:tasklet>
        </batch:step>

    <bean id="itemReader"
          class="org.springframework.batch.item.database.JdbcCursorItemReader"
          scope="step">
        <property name="dataSource" ref="dataSource"/>
        <property name="sql"
                  value="SELECT id,name,surname,transaction,date FROM JOB_REPORT"/>
        <property name="rowMapper">
            <bean class="rashjz.info.mapper.ReportRowMapper"/>
        </property>
    </bean>
    <bean id="itemWriter" class="org.springframework.batch.item.xml.StaxEventItemWriter">
        <property name="resource" value="classpath:xml/report.xml"/>
        <property name="rootTagName" value="reports"/>
        <property name="marshaller" ref="userUnmarshaller"/>
    </bean>

    <bean id="userUnmarshaller"
          class="org.springframework.oxm.xstream.XStreamMarshaller">
        <property name="aliases">
            <util:map id="aliases">
                <entry key="report" value="rashjz.info.model.Report"/>
            </util:map>
        </property>
    </bean>
    
for itemWriter we declare rootTagName for xml file and marshaller for xml file writer 
and inside resource of course we put our directory for xml files 

#####3 junit testing for batch jobs
at first we must add bean    
    `<bean class="org.springframework.batch.test.JobLauncherTestUtils"/>`
    to job-report.xml file then @Autowired JobLauncherTestUtils inside AppTest.class 
` assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());`
 in this step we check status of our running jobs 