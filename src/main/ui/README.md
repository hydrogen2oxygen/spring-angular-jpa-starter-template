# Angular UI with integration into the Spring Boot Jar

The trick is to change a setting inside angular.json.

          "options": {
            "outputPath": "../resources/static",
            "index": "src/index.html",
            "main": "src/main.ts",
            
The **outputPath** is now set on *"../resources/static"*, a folder which is packaged
automatically by Spring and served during running.

The maven plugin ...

			<plugin>
				<groupId>org.codehaus.mojo</groupId>
				<artifactId>exec-maven-plugin</artifactId>
				<version>1.6.0</version>
				<executions>
					<execution>
						<id>Build AngularDistillation</id>
						<goals>
							<goal>exec</goal>
						</goals>
						<phase>validate</phase>
						<configuration>
							<executable>ng</executable>
							<arguments>
								<argument>build</argument>
								<argument>--prod=true</argument>
							</arguments>
							<workingDirectory>${basedir}/src/main/ui</workingDirectory>
						</configuration>
					</execution>
				</executions>
			</plugin>
				
... executes **ng build** during the validation phase, even if you have not 
angular cli installed on your machine.

See [Steve's video on Spring Boot and Angular Integration](https://www.youtube.com/watch?v=JJoBUdMJf1I).
