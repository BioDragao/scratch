// Make sure that this script is executed only from the specific genome file

import ammonite.ops._
import ammonite.ops.ImplicitWd._


//var all_fastq_files = ls! pwd || grep! "\\.fastq".r


var all_fastq_files = ls! pwd |? grep! "\\.fastq".r



%("ls" , "-l")
ls!


%("pwd")
pwd


// exists! home/'BioDragon

val wd = home/'BioDragon

var all_files = ls! wd/'course_files

all_files.foreach{println}


// val reference_genome = NC000962_3.fasta

ls! pwd/up



mkdir! 'test

rm! pwd/'test


write("hello.txt", "hello")


%%('bash, "-c", "echo 'Hello' > test.txt")
%%("cat", "test.txt")

%%(root/'bin/'bash, "-c", "echo 'Hello'$ENV_ARG", ENV_ARG=123)


////



ls! pwd

ls! pwd |? grep! "sc"



