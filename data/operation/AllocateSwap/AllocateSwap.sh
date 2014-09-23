{#Main}

{!-- Allocation de la mémoire --}
sudo dd if=/dev/zero of={$fileName} bs={$base} count={$clusterNb}
sudo chmod 600 {$fileName}

{!-- Formatage --}
sudo mkswap {$fileName}
	
{!-- Utilisation --}
sudo swapon {$fileName}
	
{!-- Vérification --}
cat /proc/meminfo

{#}

