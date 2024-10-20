#version 400 core

in vec2 fragTextureCoord;
in vec3 fragNormal;
in vec3 fragPos;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;

out vec4 fragColour;

struct Material {
	vec4 ambient;
	vec4 diffuse;
	vec4 specular;
	int hasTexture;
	float reflectance;
};

uniform sampler2D textureSampler;
uniform vec3 ambientLight;
uniform Material material;
uniform vec3 lightColour;
uniform float shineDamper;
uniform float reflectivity;

vec4 ambientC;
vec4 diffuseC;
vec4 specularC;

void setupColours(Material material, vec2 texCoords) {
	if(material.hasTexture == 1) {
		ambientC = texture(textureSampler, texCoords);
		diffuseC = ambientC;
		specularC = ambientC;
	} else {
		ambientC = material.ambient;
		diffuseC = material.diffuse;
		specularC = material.specular;
	}
}

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

	if(material.hasTexture == 1) {
		ambientC = texture(textureSampler, fragTextureCoord);
	} else {
		ambientC = material.ambient + material.specular + material.diffuse + material.reflectance;
	}

	fragColour = vec4(diffuse, 1.0) * ambientC * vec4(ambientLight, 1) + vec4(finSpec, 1.0);
}













