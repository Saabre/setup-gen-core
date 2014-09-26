#!/bin/bash

# -- Operation AllocateSwap --

    # Allocation de la mémoire --
    sudo dd if=/dev/zero of=/mnt/swap.2014-09-25.18-50-20.2Gio bs=1024 count=2097152
    sudo chmod 600 /mnt/swap.2014-09-25.18-50-20.2Gio

    # Formatage --
    sudo mkswap /mnt/swap.2014-09-25.18-50-20.2Gio

    # Utilisation --
    sudo swapon /mnt/swap.2014-09-25.18-50-20.2Gio

    # Verification --
    cat /proc/meminfo

# -- Operation SetupBackend --

    cd /opt
    git clone https://github.com/Project-OSRM/osrm-backend.git
    mkdir -p build; cd build; cmake ..; make

