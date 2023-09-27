echo starting_ngrok; (nohup ./ngrok_sh.sh & sleep 10); (ps -ef | grep 'ngrok');\
echo starting_postgres; (nohup pg_ctl -D ./postgres start & sleep 10); (ps -ef | grep 'postgres');\
echo starting_springboot; (nohup ./springboot_jar_magazzino_loc_sh.sh & sleep 10);  (ps -ef | grep 'spring');(ps -ef | grep 'java -jar');     \
echo all_executed    \
