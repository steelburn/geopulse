package org.github.tess1o.geopulse.admin.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
class AdminSettingsResourcePanoramaxTest {

    @Test
    void recognizesPrettyPrintedStacVectorTileLinks() throws Exception {
        assertThat(AdminSettingsResource.hasVectorTiles(new ObjectMapper().readTree("""
                {
                  "links": [
                    { "rel": "xyz", "href": "https://example.test/tiles/{z}/{x}/{y}.mvt" }
                  ]
                }
                """))).isTrue();
    }
}
