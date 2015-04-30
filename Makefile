.PHONY:all clean


easy-rules/m2/org/easyrules/easyrules-core/1.3.1-SNAPSHOT/easyrules-core-1.3.1-SNAPSHOT.jar :
	rm -rf easy-rules
	git clone "https://github.com/benas/easy-rules.git"
	(cd easy-rules && mvn -Dmaven.repo.local=m2 clean install)
	
