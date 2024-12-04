#version 400 core

in vec2 fragTextureCoord;
in vec3 fragNormal;
in vec3 fragPos;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;
in float visibility;

out vec4 fragColour;

uniform sampler2D textureSampler;
uniform vec3 ambientLight;
uniform vec3 lightColour;
uniform float shineDamper;
uniform float reflectivity;
uniform bool lightAffected;
uniform vec3 skyColour;

vec4 texColor;

void main() {

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

	texColor = texture(textureSampler, fragTextureCoord);
	if (texColor.a < 0.5) {
		discard;
	}

	if(lightAffected) {
		if(shineDamper == -1 || reflectivity == -1) {
			fragColour = texColor * vec4(diffuse, 1.0) + (vec4(ambientLight, 1) / 2) + (texColor / 10);
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













