{#Install}
apt-get install sysstat
{#}

{#Main}
{#}

{#LoopStart}
LAST_TIMESTAMP=0

while true; do

    TIMESTAMP=`date +%s`
    if [ ! $LAST_TIMESTAMP -eq $TIMESTAMP ] 
    then

    echo ""
    echo "#TIME $TIMESTAMP"
{#}

{#LoopEnd}

    fi
    LAST_TIMESTAMP=$TIMESTAMP
    sleep 0.5

done
{#}

{#DfCsv}
    echo "#DfCsv" # Monitor remaining space
    df -k | tr -s " " | sed 's/ /; /g'
{#}

{#DfRaw}
    echo "#DfRaw" # Monitor remaining space
    df -k
{#}

{#Iostat}
    # Monitor disk usage
    echo "#Iostat" 
    iostat -d
{#}


{#MpstatAll}
    echo "#MpstatAll" # Monitor CPU usage
    mpstat -P ALL
{#}

{#Mpstat}
    echo "#Mpstat" # Monitor CPU usage
    mpstat
{#}


{#Meminfo}
    echo "#Meminfo" # Monitor RAM usage
    cat /proc/meminfo
{#}

{#Free}
    echo "#Free" # Monitor RAM usage
    free;
{#}


{#Vmstat}
    echo "#Vmstat" # Monitor all usages
    vmstat
{#}