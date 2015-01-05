{#CloneRepo}
    cd /opt
    git clone https://github.com/Project-OSRM/osrm-backend.git
{#}

{#Compile}
    cd /opt/osrm-backend/
    mkdir -p build; cd build; cmake ..; make
{#}

{#Recompile}
    cd /opt/osrm-backend/build
    make
{#}

{#ClearBuild}
    cd /opt/osrm-backend/build
    rm -f *.a
    rm -rf c*
    rm -rf C*
    rm -f l*
    rm Makefile
    rm -f stxxl*
{#}

{#BindData}
    cd /opt/osrm-backend/build/
    rm map.osm.pbf
    ln -s /opt/data/{$source}.osm.pbf map.osm.pbf
{#}

{#Extract}
    /opt/osrm-backend/build/osrm-extract map.osm.pbf
{#}

{#Prepare}
    /opt/osrm-backend/build/osrm-prepare map.osm.pbf
{#}

{#Run}
    /opt/osrm-backend/build/osrm-routed
{#}

