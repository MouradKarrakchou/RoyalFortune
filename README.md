![](/flag.png)

# Crew members
* [BONNET Kilian](https://github.com/KilianBonnet)
* [IMAMI Ayoub](https://github.com/AyoubIMAMI)
* [LE BIHAN Léo](https://github.com/LeBihanLeo)
* [KARRAKCHOU Mourad](https://github.com/MouradKarrakchou)

# Crew name
Royal Fortune's crew

# Ship Name
Royal Fortune


# Contexte
Dans le cadre de la course de bateau du module QGL, nous avons réalisé un bot en Java communiquant en JSON capable de naviguer dans différentes courses comportant chacune leurs singularités.


# The Royal Fortune Simulation (RFS)
Nous avons développé notre propre runner de course. Il est composé de 2 parties:
* Le tool Java, simule l'avancée du bateau et crée un fichier destiné au webtool
* Le Webtool, présente un rendu graphique de la course en utilisant le fichier généré par le tool Java


## Le tool Java
Notre tool Java va donc simuler l'avancer du bateau et nous permettre de voir comment va se comporter notre bot. On a réalisé le tool en utilisant la doc de l'arbitre de runner officiel, afin de pouvoir avoir une course identique à celle du runner

## Le Webtool
### Le front-end
Nous avons developpé le webtool en HTML/JS/CSS et par la suite nous avons ajouté un backend PHP.
![](/ressources/Image_RFS.png)
Lorsqu'on lance le webtool on remarque deux parties différentes:
* Le panneaux de contrôle en haut à gauche, dans lequel on insère l'input de la course que l'on souhaite visualiser, on aussi d'autre options, comme "lock/unlock" la caméra afin de suivre le bateau durant son trajet, et un bouton pour télécharger une image de la course
* C'est la mer, là ou le bateau va se déplacer et là ou l'on va afficher les récifs, les courants, les checkpoints, les balises utilisées pour le pathfinding et enfin le trajet du bateau

Toutes les entités marines représentées sont réalisées à partir de balises div, à l'exception des polygones qui sont réalisés à partir d'un Canvas. De plus le trajet du bateau est lui aussi représenté grâce à un Canvas
<br>
### Le back-end
Le back-end a été réalisé en PHP, et nous permet de stocker localement la dernière courses réalisé sur le runner
