# mutant-hunter

X-Men mutant hunter

El endpoint mutant puede ser invocado ejecutando desde la linea de comandos:

curl -X POST \
  https://mutant-hunter-307920.ue.r.appspot.com/mutant \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d '{
"dna":["AGGCGA","CGGTGC","TGATGT","AGAGGT","CCGCGA","TCACTG"]
}'

El endpoint stats puede ser consultado ejecutando desde la linea de comandos:

curl -X GET \
  https://mutant-hunter-307920.ue.r.appspot.com/stats \
  -H 'cache-control: no-cache'

