#version 400 core

// coordinates of texture pixel to be applied for current frag
in vec2 texCoord;
//
in vec3 fragNormal;
//
in vec3 fragPos;
//
in vec3 surfaceNormal;
// vector directed from surface to light
in vec3 toLightVector;
// vector from surface to camera
in vec3 toCameraVector;
// visibility of the area
in float visibility;

// final parameter - color of the pixel
out vec4 fragColour;

/// albedo map
uniform sampler2D albedoMap;
/// normal map
uniform sampler2D normalMap;
/// roughness map
uniform sampler2D roughnessMap;
/// metallic map
uniform sampler2D metallicMap;

/// color of shadows and fog
uniform vec3 ambientLight;
/// color of light source
uniform vec3 lightColour;
/// shiny dumber (will be replaced with metallic & roughness maps)
uniform float shineDamper;
/// will be removed
//uniform float reflectivity;
/// will be removed
uniform bool lightAffected;

vec4 texColor;


void main() {

	/*
	vec3 unitNormal = normalize(surfaceNormal);
	vec3 unitLightVector = normalize(toLightVector);

	float nDot1 = dot(unitNormal, unitLightVector);
	float brightness = max(nDot1, 0.0);
	vec3 diffuse = brightness * lightColour;
	
	vec3 unitVecToCam = normalize(toCameraVector);
	vec3 lightDir = -unitLightVector;
	vec3 reflectedLightDir = reflect(lightDir, unitNormal);
	
	float specFactor = dot(reflectedLightDir, unitVecToCam);
	specFactor = max(specFactor, 0.0);
	float dampedFactor = pow(specFactor, shineDamper);
	vec3 finSpec =  dampedFactor * lightColour;
	*/

	texColor = texture(albedoMap, fragTextureCoord);
	float metalness = texture(metallicMap, fragTextureCoord).r;
	float roughness = texture(roughnessMap, fragTextureCoord).r;
	
	if (texColor.a < 0.5) {
		discard;
	}

	if(lightAffected) {
		if(shineDamper == -1 || reflectivity == -1) {
			fragColour = texColor * vec4(diffuse, 1) + (vec4(ambientLight, 1) / 2) + (texColor / 10);
			fragColour = mix(vec4(skyColour, 1.0), fragColour, visibility);
		} else {
			fragColour = vec4(diffuse, 1.0) * texColor + vec4(finSpec, 1.0) + ((vec4(ambientLight, 1) / 2) + (texColor / 10));
			fragColour = mix(vec4(skyColour, 1.0), fragColour, visibility);
		}
	} else {
		fragColour = texColor;
		fragColour = mix(vec4(skyColour, 1.0), fragColour, visibility);
	}
}
