{#Install}
apt-get install sysstat
{#}

{#Main}
# Prepare the file
FILE="monitor.log"
echo "" > $FILE
{#}

{#LoopStart}
LAST_TIMESTAMP=0

#while true; do

    TIMESTAMP=`date +%s`
    if[! $LAST_TIMESTAMP -eq $TIMESTAMP]

    echo "" >> $FILE
    echo "TIME $TIMESTAMP" >> $FILE
{#}

{#LoopEnd}
    
    fi
    LAST_TIMESTAMP=$TIMESTAMP
    sleep 0.5
    
#done
{#}

{#DfCsv}
    # Monitor remaining space
    df -k | tr -s " " | sed 's/ /; /g' >> $FILE
{#}

{#DfRaw}
    # Monitor remaining space
    df -k >> $FILE
{#}

{#Iostat}
    # Monitor disk usage
    iostat -d >> $FILE
{#}


{#MpstatAll}
    # Monitor CPU usage
    mpstat -P all >> $FILE
{#}

{#Mpstat}
    # Monitor CPU usage
    mpstat >> $FILE
{#}


{#Meminfo}
    # Monitor RAM usage
    cat /proc/meminfo >> $FILE
{#}

{#Free}
    # Monitor RAM usage
    free >> $FILE
{#}


{#Vmstat}
    # Monitor all usages
    vmstat >> $FILE
{#}