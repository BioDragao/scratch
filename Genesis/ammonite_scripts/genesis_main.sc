import $file.genesis_utils


////////////////////////////////////////////////
// EXERCISE - 1
// PAGE - 20
////////////////////////////////////////////////




// "PT000033_1.fastq.gz" =>  "PT000033_1.fastq"

// generate_fastq_names_from_fastqgz( "PT000033_1.fastq.gz")

def generate_fastq_names_from_fastqgz(fastqgz_name:String) : String =
{
  var name_array = fastqgz_name.split("\\.")
  return { name_array(0) + ".fastq"}
}


def gzip_decompression(fastqgz_name:String) = {

  var fastq_name = generate_fastq_names_from_fastqgz(fastqgz_name)

  println("gzip -dc " + fastqgz_name + " > " + fastq_name )

}


////////////////////////////////////////////////
// EXERCISE - 3
// PAGE - 26
////////////////////////////////////////////////






// generate_trimmed_name("PT000033_1.fastq")
def generate_trimmed_name(genome_name:String) : String =
{
  var name_array = genome_name.split("\\.")
  return { name_array(0) + "_trimmed_paired.fastq"}

}


// generate_untrimmed_name("PT000033_1.fastq")
def generate_untrimmed_name(genome_name:String) : String =
{

  var name_array = genome_name.split("\\.")
  return { name_array(0) + "_trimmed_unpaired.fastq"}

}


def trimmomatic(genome_name:String) = {

// java -jar /opt/Trimmomatic-0.36/trimmomatic-0.36.jar PE -phred33 PT000033_1.fastq PT000033_2.fastq PT000033_1_trimmed_paired.fastq PT000033_1_trimmed_unpaired.fastq PT000033_2_trimmed_paired.fastq PT000033_2_trimmed_unpaired.fastq LEADING:3 TRAILING:3 SLIDINGWINDOW:4:20 MINLEN:36



var  genome_first = genome_name + "_1.fastq"
var  genome_second = genome_name + "_2.fastq"


  println("java -jar /opt/Trimmomatic-0.36/trimmomatic-0.36.jar PE -phred33 " +  genome_first +  " " + genome_second + " " + generate_trimmed_name(genome_first) +   " " + generate_untrimmed_name(genome_first) +   " " + generate_trimmed_name(genome_second) +  " " + generate_untrimmed_name(genome_second) + " LEADING:3 TRAILING:3 SLIDINGWINDOW:4:20 MINLEN:36")

}



////////////////////////////////////////////////
// EXERCISE - 4
// PAGE - 27
////////////////////////////////////////////////




// bwa_index_reference_genome("NC000962_3.fasta")
def bwa_index_reference_genome(reference_genome:String) = {

  println("bwa index " + reference_genome)
}



// map_and_generate_sam_file("PT000033", "NC000962_3.fasta", "PT000033_1_trimmed_paired.fastq", "PT000033_2_trimmed_paired.fastq")
def map_and_generate_sam_file(genome_name:String, reference_genome:String , genome_1_trimmed:String , genome_2_trimmed:String ) = {


  //  bwa mem -R "@RG\tID:PT000033\tSM:PT000033\tPL:Illumina" -M NC000962_3.fasta PT000033_1_trimmed_paired.fastq PT000033_2_trimmed_paired.fastq > PT000033.sam


  var sam_file_name = genome_name.split("\\.")(0) + ".sam"

  println("bwa mem -R \"@RG\\tID:" + genome_name + "\\tSM:" + genome_name + "\\tPL:Illumina\" -M " + reference_genome + " " + genome_1_trimmed + " "+ genome_2_trimmed + " > " + sam_file_name)

}


// samtools_faidx_reference_genome("NC000962_3.fasta")
def samtools_faidx_reference_genome(reference_genome:String) = {

  println("samtools faidx " + reference_genome)
}


// convert_sam_file_to_bam_file( "NC000962_3.fasta", "PT000033")
def convert_sam_file_to_bam_file(reference_genome:String, genome_name:String) = {

  // If this file doesn't exist then execute the bwa_index_reference_genome function
  var fai_from_reference_genome = reference_genome + ".fai"

  var sam_file_name = genome_name.split("\\.")(0) + ".sam"

  var bam_file_name = genome_name.split("\\.")(0) + ".bam"

  println("samtools view -bt " + fai_from_reference_genome + " " +  sam_file_name  + " > " + bam_file_name)

}



// samtools_index_sorted_bam("PT000033")
def samtools_index_sorted_bam(genome_name:String) = {

  var sorted_bam_file_name = genome_name.split("\\.")(0) + ".sorted.bam"

  println("samtools index " + sorted_bam_file_name)
}




// sort_bam_file("PT000033")
def sort_bam_file(genome_name:String) = {

  //  samtools sort PT000033.bam -o PT000033.sorted.bam

  var bam_file_name = genome_name.split("\\.")(0) + ".bam"

  var sorted_bam_file_name = genome_name.split("\\.")(0) + ".sorted.bam"

  println("samtools sort " + genome_name + " " + bam_file_name + " -o " + sorted_bam_file_name)

}



// mapping_statistics("PT000033")
def mapping_statistics(genome_name:String) = {

  var sorted_bam_file_name = genome_name.split("\\.")(0) + ".sorted.bam"

  var stats_text_file = genome_name + "_stats.txt"

  println("samtools flagstat " + sorted_bam_file_name + " > " + stats_text_file)
}




////////////////////////////////////////////////
// VARIANT CALLING
// EXERCISE - 6
// PAGE - 27
////////////////////////////////////////////////




// samtools_mpileup("NC000962_3", "PT000033")
def samtools_mpileup(reference_genome:String, genome_name:String) = {

  var sorted_bam_file_name = genome_name.split("\\.")(0) + ".sorted.bam"

  var raw_vcf_file_name = genome_name.split("\\.")(0) + ".raw.vcf"

  println("samtools mpileup -Q 23 -d 2000 -C 50 -ugf " + reference_genome + " " +  sorted_bam_file_name + " | bcftools call -O v -vm -o " + raw_vcf_file_name)

}


// vcfutils_filter("PT000033")
def vcfutils_filter(genome_name:String) = {

  var raw_vcf_file_name = genome_name.split("\\.")(0) + ".raw.vcf"

  var filt_vcf_file_name = genome_name.split("\\.")(0) + ".filt.vcf"

  println("vcfutils.pl varFilter -d 10 -D 2000 " + raw_vcf_file_name + " > "  +  filt_vcf_file_name)

}


// bgzip_filt_file("PT000033")
def bgzip_filt_file(genome_name:String) = {

  var filt_vcf_file_name = genome_name.split("\\.")(0) + ".filt.vcf"

  var bgzip_vcf_file_name = genome_name.split("\\.")(0) + ".filt.vcf.gz"

  println("bgzip -c " +  filt_vcf_file_name + " > "  + bgzip_vcf_file_name )

}


// run_tabix("PT000033")
def run_tabix(genome_name:String) = {

  var bgzip_vcf_file_name = genome_name.split("\\.")(0) + ".filt.vcf.gz"

  println("tabix -p vcf " + bgzip_vcf_file_name )

}


// snpEff("NC000962_3", "PT000033")
def snpEff(reference_genome:String, genome_name:String) = {

// java -Xmx4g -jar /opt/snpEff/snpEff.jar -no-downstream -no-upstream - v -c /opt/snpEff/snpEff.config NC000962_3 PT000033.filt.vcf > PT000033.ann.vcf

  var filt_vcf_file_name = genome_name.split("\\.")(0) + ".filt.vcf"

  var ann_vcf_file_name = genome_name.split("\\.")(0) + ".ann.vcf.gz"

  println("java -Xmx4g -jar /opt/snpEff/snpEff.jar -no-downstream -no-upstream - v -c /opt/snpEff/snpEff.config " + reference_genome + " " + filt_vcf_file_name + " > " + ann_vcf_file_name)
}


