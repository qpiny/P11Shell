package org.rejna.pkcs11;

import java.util.List;

import org.bridj.Platform;
import org.bridj.StructIO;
import org.bridj.StructIO.AggregatedFieldDesc;
import org.bridj.StructIO.DefaultCustomizer;

public class DisableAlignmentIfWindows extends DefaultCustomizer {

	@Override
	public void beforeLayout(StructIO io, List<AggregatedFieldDesc> aggregatedFields) {
		if (Platform.isWindows())
			for (AggregatedFieldDesc f: aggregatedFields)
				f.alignment = 1;	
	}
}
