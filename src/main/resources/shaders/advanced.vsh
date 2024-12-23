#version 400 core

// model position
in vec3 position;
// coords of texture
in vec2 textureCoord;
// normal
in vec3 normal;

// changed coords of tex
out vec2 texCoord;
// changed frag normal
out vec3 fragNormal;
// changed frag pos
out vec3 fragPos;
// idk
out vec3 surfaceNormal;
// surface -> light
out vec3 toLightVector;
// surface -> cam
out vec3 toCameraVector;
// for fog
out float visibility;

// transform model
uniform mat4 transformationMatrix;
// projection of 3D scene to our 2D monitor
uniform mat4 projMatrix;
/* 
	Converts world space to camera space
In computer graphics, you can't move the camera
in the usual sense. Instead of it, you literally
move all the scene to create an illusion of
camera movement. This matrix is something like
transformation matrix, but for camera.
*/
uniform mat4 viewMatrix;
// pos of light in world
uniform vec3 lightPosition;
// density and gradient values for fog
uniform vec2 fogParams;
// normal map
uniform sampler2D normalMap;


const float density = 0.02;
const float gradient = 1.5;

void main() {

	/// 3D INIT
	// converting model position to world position
	vec4 worldPos = transformationMatrix * vec4(position, 1.0);
	// converting world space to camera space
	vec4 posRelativeToCam = viewMatrix * worldPos;
	// defines vertex position in the screenspace
	gl_Position = projMatrix * posRelativeToCam;
	
	// vector that is perpendicular to the fragment
	fragNormal = normalize(worldPos).xyz;
	fragPos = worldPos.xyz; 
	texCoord = textureCoord;
	
	surfaceNormal = (transformationMatrix * vec4(normal, 0.0)).xyz;
	toLightVector = lightPosition - worldPos.xyz;
	toCameraVector = (inverse(viewMatrix) * vec4(0.0,0.0,0.0,1.0)).xyz - worldPos.xyz;

	float distance = length(posRelativeToCam.xyz);
	visibility = exp(-pow(distance * fogParams.x, fogParams.y));
	visibility = clamp(visibility, 0.0, 1.0);
}
