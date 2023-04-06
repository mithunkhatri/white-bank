echo '----------- Starting maven install -----------'
pwd
./mvnw clean install
echo '----------- Completed maven install -----------'

echo '----------- Starting docker builds -----------'
docker-compose build
echo '----------- Completed docker builds -----------'

echo '----------- Starting applications -----------'
docker-compose up -d
echo '----------- Applications started and integrated successfully -----------'