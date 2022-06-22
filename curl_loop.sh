URL="http://localhost:8080/"
i=0 # set counter to 0
while true  # infinite loop
do
    curl -s ${URL}  # silent curl request to site
    i=$(($i+1))  # increment counter
    echo -en "$i        \r"   # display # of requests each iteration
    sleep 1  # short pause between requests
done
