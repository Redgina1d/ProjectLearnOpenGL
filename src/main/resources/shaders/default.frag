#version 430 core

in vec2 fragTextureCoord;
in vec3 fragNormal;
in vec3 fragPos;
in vec3 surfaceNormal;
in vec3 toLightVecs[];
in vec3 toCamVec;
in float fog;

out vec4 fragColour;

uniform sampler2D albedoMap;
uniform vec3 ambLight;
uniform int lightsNumber;
uniform vec3 lightColor[];
uniform float shineDamper;

vec4 texColor;

void main() {

	vec3 unitNormal = normalize(surfaceNormal);

	vec3 normalLight;
	float nDot1;
	float bright;
	vec3 diff;
	vec3 normalToCam;
	vec3 reflectedDir;
	float specFac;
	float dampFac;
	vec3 finLight;

	for (int i = 0; i < lightsNumber; i++) {
		// from frag to light vec
		normalLight = normalize(toLightVecs[i]);
		// scalar product of surface normal and normal vec
		nDot1 = dot(unitNormal, normalLight);
		// rate how bright is frag from 0 to 1
		bright = max(nDot1, 0.0);
		// bright colorrrrr
		diff = bright * lightColor[i];
		// m
		normalToCam = normalize(toCamVec);
		// reflected light vec
		reflectedDir = reflect(-normalLight, unitNormal);
		// determine how bright is frog by finding vec between reflected and toCam
		specFac = dot(reflectedDir, normalize(toCamVec));
		specFac = max(specFac, 0.0);
		// rrr
		dampFac = pow(specFac, shineDamper);
		
		// adds one of lights to the pixel
		finLight += dampFac * lightColor[i];		
	}
	
	texColor = texture(albedoMap, fragTextureCoord);

	if (texColor.a < 0.5) {
		discard;
	}


	if (shineDamper == -1) {
		fragColour = texColor * vec4(diff, 1.0) + (vec4(ambLight, 1) / 2) + (texColor / 10);
		fragColour = mix(vec4(ambLight, 1.0), fragColour, fog);
	} else {
		fragColour = vec4(diff, 1.0) * texColor + vec4(finLight, 1.0) + ((vec4(ambLight, 1) / 2) + (texColor / 10));
		fragColour = mix(vec4(ambLight, 1.0), fragColour, fog);
	}
	
}
