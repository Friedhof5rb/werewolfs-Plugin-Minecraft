# werewolfs-Plugin-Minecraft

Dies ist ein Spigot-Plugin für Minecraft-Server mit dem sich das Kartenspiel Werwolf in Minecraft spielen lässt.
Es ist darauf ausgelegt zusammen mit dem Simple-Voice-Chat-Plugin auf Server Side und dem Simple-Voice-Chat-Mod auf Client-Side verwendet zu werden.

Das Plugin sollte möglichst auf einer geschlossenen Map verwendet werden und erfordert einen Spielleiter der mit /gui auf alle möglichen Befehle zugreifen kann.

Das Plugin basiert auf folgendem Konzept:

Setting: Ein Dorf gebaut in Minecraft, 
keiner kann Blöcke abbauen oder Leute schlagen, 
Leute schlagen nur möglich mit speziellem Schwert

Alles wird über Proximity-Voicechat besprochen → Dafür muss jeder auch einen Mod downloaden

Möglichkeit Individuell Spieler für Spieler „nicht existent“ zu machen
Heißt:
Spieler A existiert für Spieler B nicht → Spieler B sieht und hört A nicht
umgekehrt ist es trotzdem möglich

Rechts werden in einer Tabelle alle lebenden Spieler angezeigt 
Generell haben alle Spieler den Glow-Effekt damit sich niemand verstecken kann

Tagphase und Nachtphase:

Basierend auf Timer von z-B. 5 Minuten oder Spielleiterentscheidung per Command
(Der Spielleiter würde alle sehen und hören, auch nachts)

Tagphase:

Bei Tag finden die üblichen Gespräche statt, nur in Minecraft
Alle sehen und hören einander
Leute können rausgevotet werden mithilfe von Chat commands(ein Opfer pro Tag)
Tote Spieler sind generell im Spectator Mode und können sich alles anschauen und anhören

Nachtphase:

In der Nacht kommen die Rollen zum Einsatz.

Dorfbewohner: sieht und hört Niemanden

Werwölfe: 
sehen und hören nur andere Werwölfe
können ein Opfer voten, dieses ist dann sichtbar für alle Werwölfe umgekehrt aber nicht

Die Werwölfe erhalten dann auch alle ein Schwert mit welchem sie nur den ausgewählten Spieler töten können, dieses kann nicht gedroppt werden und es verschwindet nach Benutzung von einem Werwolf bei allen Werwölfen

Seherin:
Kann eine Person auswählen , die Sie für den Rest der Nacht hört und sieht(umgekehrt aber nicht)



Jäger:
Wenn er stirbt respawnt er und kann eine Person auswählen die er sehen und hören will und er erhält ein Schwert für diese Person → bei Tag stirbt er endgültig

Hexe:
kann einmal pro Game jeweils einmal heilen und töten

Kann Opfer per Command auswählen, sieht und hört es dann und erhält Schwert
Erfährt jede Nacht Werwolfopfer, kann es einfach per Command wiederbeleben
