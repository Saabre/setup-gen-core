{#Install}
apt-get install sysstata dstat
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
    echo "#Iostat" # Monitor disk usage
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

{#TopGrep}
    echo "#Top" # Monitor CPU usage
    top -bn 2 -d 0.01 | grep '^%Cpu' | tail -n 1
{#}


{#Meminfo}
    echo "#Meminfo" # Monitor RAM usage
    egrep 'Mem|Cache|Swap' /proc/meminfo
{#}

{#Free}
    echo "#Free" # Monitor RAM usage
    free;
{#}


{#Vmstat}
    echo "#Vmstat" # Monitor all usages
    vmstat
{#}