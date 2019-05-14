安装jar包到本地仓库

mvn install:install-file -DgroupId=com.google.code -DartifactId=kaptcha -Dversion=2.3 -Dfile=kaptcha-2.3.2.jar -Dpackaging=jar -DgeneratePom=true