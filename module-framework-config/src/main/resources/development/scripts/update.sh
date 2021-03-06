#! /bin/bash
echo -e "\n### lsxy server check ###\n"

if [ $# = 0 ];then
        echo "Use: $0 -j [start|stop|status|restart|mystart|log] -a app-portal-api -r 1.2.0-RC3 -h p5a"
        exit 1
fi



while getopts :a:r:h:j: ARGS
do
 case $ARGS in
     a)
#         echo "Found the -a option"
#         echo "The parameter follow -a is $OPTARG"
                 app_name=$OPTARG
         ;;
     r)
 #        echo "Found the -r option"
 #        echo "The parameter follow -r is $OPTARG"
                 r_name=$OPTARG
         ;;
     h)
  #       echo "Found the -h option"
  #       echo "The parameter follow -h is $OPTARG"
                 host_name=$OPTARG
         ;;
     j)
   #      echo "Found the -j option"
   #      echo "The parameter follow -j is $OPTARG"
                 action_name=$OPTARG
         ;;
     ?)
          echo "Use: $0 -j [start|stop|status|restart|mystart|log] -a app-portal-api -r 1.2.0-RC3 -h p5a"
          exit 1
         ;;
 esac
done

if [ "$app_name"x = "app-portal"x ]
then
    echo "ssh root@$host_name /opt/tomcat_app_portal/bin/startup.sh"
    ssh root@$host_name "nohup /opt/yunhuni-peer-internet/bin/update_restart.sh -A $app_name -D -P development -I -M /opt/tomcat_app_portal >> /opt/yunhuni/logs/$app_name.out 2>&1 &"
else
    echo "ssh root@$host_name nohup /opt/yunhuni-peer-internet/bin/update_restart.sh -A $app_name -S -P development -I >> /opt/yunhuni/logs/$app_name.out 2>&1 &"
    ssh root@$host_name "nohup /opt/yunhuni-peer-internet/bin/update_restart.sh -A $app_name -S -P development -I >> /opt/yunhuni/logs/$app_name.out 2>&1 &"
fi

