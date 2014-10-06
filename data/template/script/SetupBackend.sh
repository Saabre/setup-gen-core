{#CloneRepo}
    cd /opt
    git clone https://github.com/Project-OSRM/osrm-backend.git
{#}

{#Compile}
    cd /opt/osrm-backend/
    mkdir -p build; cd build; cmake ..; make
{#}

{#ClearBuild}
    rm -rf /opt/osrm-backend/build
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

