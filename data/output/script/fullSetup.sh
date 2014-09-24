#!/bin/bash

# -- Operation AllocateSwap --

    # Allocation de la mémoire --
    sudo dd if=/dev/zero of=/mnt/swap1.2g bs=1024 count=2097152
    sudo chmod 600 /mnt/swap1.2g

    # Formatage --
    sudo mkswap /mnt/swap1.2g

    # Utilisation --
    sudo swapon /mnt/swap1.2g

    # Verification --
    cat /proc/meminfo

# -- Operation SetupBackend --

    cd /opt
    git clone https://github.com/Project-OSRM/osrm-backend.git
    mkdir -p build; cd build; cmake ..; make

