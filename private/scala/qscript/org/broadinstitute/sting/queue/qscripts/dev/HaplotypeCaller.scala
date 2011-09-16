package org.broadinstitute.sting.queue.qscripts

import org.broadinstitute.sting.queue.extensions.gatk._
import org.broadinstitute.sting.queue.QScript
import org.broadinstitute.sting.gatk.phonehome.GATKRunReport

class HaplotypeCallerScript extends QScript {
  qscript =>

  @Argument(shortName="out", doc="output file", required=true)
  var out: String = "."
  @Argument(shortName="ref", doc="ref file", required=true)
  var ref: String = "."
  @Argument(shortName="bam", doc="bam file", required=true)
  var bam: String = "."
  @Argument(shortName="interval", doc="interval file", required=true)
  var interval: String = "."

  trait UNIVERSAL_GATK_ARGS extends CommandLineGATK {
    memoryLimit = 2;
  }

  def script = {

    val hc = new HaplotypeCaller with UNIVERSAL_GATK_ARGS
    hc.reference_sequence = new File(ref)
    hc.intervalsString ++= List(interval)
    hc.scatterCount = 100
    hc.input_file :+= new File(bam)
    hc.o = new File(out)
    add(hc)
  }

}