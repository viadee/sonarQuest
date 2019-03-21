import { Component, OnInit } from '@angular/core';
import { UserSkill } from '../../Interfaces/UserSkill';
import { SkillTreeService } from '../../services/skill-tree.service';
import { NestedTreeControl } from '@angular/cdk/tree';
import { MatTreeNestedDataSource } from '@angular/material/tree';


@Component({
    selector: 'app-skill-tree-page',
    templateUrl: './skill-tree-page.component.html',
    styleUrls: ['./skill-tree-page.component.css']
})
export class SkillTreePageComponent implements OnInit {
    userskills: UserSkill[];

    treeControl: NestedTreeControl<UserSkill>;
    dataSource: MatTreeNestedDataSource<UserSkill>;
    /* TREE_DATA: UserSkill[] = [
       {
            'id': 1,
            'description': 'IF-Verzweigungen',
            'name': 'IF',
            'followingUserSkills': [
                {
                    'id': 2,
                    'description': 'Array',
                    'name': 'Array',
                    'followingUserSkills': [
                        {
                            'id': 3,
                            'description': 'Loop',
                            'name': 'Loop',
                            'followingUserSkills': [],
                            'sonarRules': [
                                {
                                    'name': 'Servlets should not have mutable instance fields',
                                    'key': 'squid:S2226'
                                }
                            ],
                            'userSkillGroup': {
                                'id': 1,
                                'name': 'Java-Basics'
                            },
                            'root': false
                        }
                    ],
                    'sonarRules': [
                        {
                            'name': 'Servlets should not have mutable instance fields',
                            'key': 'squid:S2226'
                        }
                    ],
                    'userSkillGroup': {
                        'id': 1,
                        'name': 'Java-Basics'
                    },
                    'root': false
                }
            ],
            'sonarRules': [
                {
                    'name': 'Servlets should not have mutable instance fields',
                    'key': 'squid:S2226'
                }
            ],
            'userSkillGroup': {
                'id': 1,
                'name': 'Java-Basics'
            },
            'root': true
        },
        {
            'id': 2,
            'description': 'Array',
            'name': 'Array',
            'followingUserSkills': [
                {
                    'id': 3,
                    'description': 'Loop',
                    'name': 'Loop',
                    'followingUserSkills': [],
                    'sonarRules': [
                        {
                            'name': 'Servlets should not have mutable instance fields',
                            'key': 'squid:S2226'
                        }
                    ],
                    'userSkillGroup': {
                        'id': 1,
                        'name': 'Java-Basics'
                    },
                    'root': false
                }
            ],
            'sonarRules': [
                {
                    'name': 'Servlets should not have mutable instance fields',
                    'key': 'squid:S2226'
                }
            ],
            'userSkillGroup': {
                'id': 1,
                'name': 'Java-Basics'
            },
            'root': false
        },
        {
            'id': 3,
            'description': 'Loop',
            'name': 'Loop',
            'followingUserSkills': [],
            'sonarRules': [
                {
                    'name': 'Servlets should not have mutable instance fields',
                    'key': 'squid:S2226'
                }
            ],
            'userSkillGroup': {
                'id': 1,
                'name': 'Java-Basics'
            },
            'root': false
        }
    ];*/

    constructor(private skillTreeService: SkillTreeService) {

        this.skillTreeService.userskills$.subscribe(userskills => {
            this.userskills = userskills;
        });
        this.skillTreeService.getData();
        this.treeControl = new NestedTreeControl<UserSkill>(node => node.followingUserSkills);
        this.dataSource = new MatTreeNestedDataSource<UserSkill>();
                this.dataSource.data = this.userskills;
    }



    hasChild = (_: number, node: UserSkill) => !!node.followingUserSkills && node.followingUserSkills.length > 0;

    ngOnInit() {
       /* this.skillTreeService.userskills$.subscribe(userskills => {
            this.userskills = userskills;
        });

        this.skillTreeService.getData();
        this.dataSource.data = this.userskills;*/
    }

}

